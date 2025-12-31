package com.example.coffeeshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.coffeeshop.DTO.Request.CategoryRequest;
import com.example.coffeeshop.DTO.Response.CategoryResponse;
import com.example.coffeeshop.models.entities.Category;
import com.example.coffeeshop.models.repository.CategoryRepository;

import jakarta.transaction.Transactional;

public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse create(CategoryRequest req) {
        if (categoryRepository.existsByNameIgnoreCase(req.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name sudah ada");
        }

        Category category = new Category();
        category.setName(req.getName());
        category.setDescription(req.getDescription());
        category.setIsActive(req.getIsActive() != null ? req.getIsActive() : true);

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        return toResponse(category);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        // kalau name diganti, cek unik
        if (req.getName() != null && !category.getName().equalsIgnoreCase(req.getName())) {
            if (categoryRepository.existsByNameIgnoreCase(req.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name sudah ada");
            }
            category.setName(req.getName());
        }

        category.setDescription(req.getDescription());
        if (req.getIsActive() != null)
            category.setIsActive(req.getIsActive());

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setDescription(category.getDescription());
        categoryResponse.setIsActive(category.getIsActive());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        return categoryResponse;
    }
}
