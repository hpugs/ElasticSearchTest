package com.hpugs.elasticsearch.dto;

import com.hpugs.elasticsearch.EnumEsConst;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Document(indexName = EnumEsConst.PHONE_INDEX_NAME)
public class PhoneModel {

    @Id
    private String id;

    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word"),
            otherFields = @InnerField(type = FieldType.Text, suffix = "pinyin", analyzer = "pinyin"))
    private String title;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Text)
    private String images;

    @Field(type = FieldType.Text)
    private String prices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
}
