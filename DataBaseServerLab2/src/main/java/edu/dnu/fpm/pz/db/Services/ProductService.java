package edu.dnu.fpm.pz.db.Services;

import edu.dnu.fpm.pz.db.Entities.Product;
import edu.dnu.fpm.pz.db.Entities.User;
import edu.dnu.fpm.pz.db.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll() { return repo.findAll(); }
    public Product getById(Long id) { return repo.findById(id).orElse(null); }
    public Product save(Product p) { return repo.save(p); }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public ResponseEntity<String> updateProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }
        Product productToUpdate = productOptional.get();
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCategory(product.getCategory());
        productToUpdate.setDescription(product.getDescription());
        productRepository.save(productToUpdate);
        return ResponseEntity.ok("Product updated successfully");
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}