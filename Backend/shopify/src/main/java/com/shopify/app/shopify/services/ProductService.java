package com.shopify.app.shopify.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shopify.app.shopify.entity.Product;
import com.shopify.app.shopify.entity.Role;
import com.shopify.app.shopify.entity.User;
import com.shopify.app.shopify.repositories.ProductRepositories;
import com.shopify.app.shopify.repositories.UserRepositories;

import jdk.internal.util.xml.impl.Input;

@Service
public class ProductService {

	@Autowired
	private ProductRepositories repositories;
	
	private UserRepositories userRepositories;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	private static final String BASE_URL = "http://localhost:8080/images/"; 
	
	public void saveProduct(String productTitle, double originalPrice, int discountPercentage,
			String description,int ratings, int productQuantity,
			String productCategory, MultipartFile image) throws IOException {
		
		// create uploads folder if not exist
		File uploadfolder = new File(uploadDir);
		
		if( !uploadfolder.exists()) {
			uploadfolder.mkdir();
		}
		
		// Multipartfile => image data, image name, image type
		byte[] imageData = image.getBytes();
		String imageName = image.getOriginalFilename();
		String imageType = image.getContentType();
		
		
		// check image type is valid or not
		
		if(imageType == null || !imageType.startsWith("image/")) {
			throw new IllegalArgumentException("Invalid image type");
		}
		
		// Generate Random ID for Image
		// create filepath ( uploads, file-serve(Angular))
		// UUID => 5435875_sample.jpg
		String newImageName = UUID.randomUUID() + "_" + imageName;
		
		// uploads/5435875_sample.jpg
		Path uploadImagePath = Paths.get(uploadDir, newImageName);
		
		// http://localhost:8080/images/5435875_sample.jpg - Angular Project
		String imageServePath = BASE_URL + newImageName;
		
		// save file in uploads folder
		Files.write(uploadImagePath, imageData);
		
		// calculate selling price
		double sellingPrice = calculateSellingPrice(originalPrice, discountPercentage);
		
		
		
		
		// create product entity object
		Product product = new Product(productTitle, originalPrice, sellingPrice, discountPercentage, description,
				productQuantity, productCategory, imageServePath, newImageName, imageType, ratings);

		// calling repository saveProduct method to save product into database
		repositories.saveProduct(product);
		
		
	}

	public List<Product> getAllProduct() {
		List<Product> list = null;
		try {
			
			list = repositories.getAllProduct();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public Product getProductByProductId(int id) {
		Product product = null;
		try {
			product = repositories.getProductByProductId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return product;
	}

	
	public void updateProduct(long productId, String productTitle, double originalPrice, int discountPercentage,
			String description, int productQuantity, String productCategory, MultipartFile image) throws IOException {

		// Find Product By id from database
		Product existingProduct = repositories.getProductByProductId(productId);
		
		if(existingProduct == null) {
			throw new RuntimeException("Product is not found with Id : " + productId);
		}
		
		// set product updated details
		if( productTitle != null || originalPrice > 0.0 ||
				description != null || productCategory != null
				) 
		{
			existingProduct.setProductTitle(productTitle);
			existingProduct.setOriginalPrice(originalPrice);
			existingProduct.setDiscountPercentage(discountPercentage);
			existingProduct.setDescription(description);
			existingProduct.setProductQuantity(productQuantity);
			existingProduct.setProductCategory(productCategory);
			// calculate selling price
			double sellingPrice = calculateSellingPrice(originalPrice, discountPercentage);
			existingProduct.setSellingPrice(sellingPrice);
		
		}
		
		// if image path is found then delete the image using file class delete method and set new image details
		String productImageName = existingProduct.getProductImageName();
		if( image != null && !image.isEmpty()) {
			
			File file = new File(uploadDir + "/" +productImageName);
			if( file.exists()) {
				file.delete();
				System.out.println("File delete from uploads folder");
			}else {
				System.out.println("File is not found");
			}
			
			// save updated image in uploads folder
			String imageName = image.getOriginalFilename();
			String newImageName = UUID.randomUUID() + "_" + imageName;
			
			// uploads/5435875_sample.jpg
			Path uploadImagePath = Paths.get(uploadDir, newImageName);
			
			// http://localhost:8080/images/5435875_sample.jpg - Angular Project
			String imageServePath = BASE_URL + newImageName;
			
			try( InputStream stream = image.getInputStream()) {
				Files.copy(stream, uploadImagePath,StandardCopyOption.REPLACE_EXISTING);
			}
			// set image details
			
			existingProduct.setProductImageName(newImageName);
			existingProduct.setProductImagePath(imageServePath);
			existingProduct.setProductImageType(image.getContentType());
			
		}
		
		// call repositories updateProduct method to update database
		repositories.updateProduct(existingProduct);
	
	}
	
	public void deleteProduct(int id) {
	// Find Product By id from database
		Product existingProduct = repositories.getProductByProductId(id);
					
		if(existingProduct == null) {
			throw new RuntimeException("Product is not found with Id : " + id);
		}	
		
		String productImageName = existingProduct.getProductImageName();
		
			
		File file = new File(uploadDir + "/" +productImageName);
		if( productImageName != null && !productImageName.isEmpty()) {
			if( file.exists()) {
				file.delete();
			}else {
				System.out.println("File is not found");
			}
		}	
		
		repositories.deleteProduct(id);
	}
	
	// calculate selling price
	private double calculateSellingPrice(double originalPrice,int discountPercentage) {
		double discount = (originalPrice * discountPercentage) / 100.0;
		double sellingPrice = originalPrice - discount;
		return sellingPrice;
	}

	

	
	

	
}
