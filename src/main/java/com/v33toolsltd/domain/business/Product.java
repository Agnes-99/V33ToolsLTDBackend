package com.v33toolsltd.domain.business;


import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String image_url;
    private double price;
    private int stockQuantity;
    private int categoryId;

    @Enumerated(EnumType.STRING)
    private Brands brand;

    protected Product() {
    }

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.image_url = builder.image_url;
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;
        this.categoryId = builder.categoryId;
        this.brand = builder.brand;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Brands getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image_url='" + image_url + '\'' +
                ", price=" + price +
                ", stock quantity=" + stockQuantity +
                ", categoryId=" + categoryId +
                ", brand=" + (brand != null ? brand.getDisplayName() : null) +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String image_url;
        private double price;
        private int stockQuantity;
        private int categoryId;
        private Brands brand;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setImage_url(String image_url) {
            this.image_url = image_url;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder setCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setBrand(Brands brand) {
            this.brand = brand;
            return this;
        }

        public Builder copy(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.description = product.getDescription();
            this.image_url = product.getImage_url();
            this.price = product.getPrice();
            this.stockQuantity = product.getStockQuantity();
            this.categoryId = product.getCategoryId();
            this.brand = product.getBrand();
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}

