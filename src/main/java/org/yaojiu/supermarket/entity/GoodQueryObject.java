package org.yaojiu.supermarket.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodQueryObject {
    private Integer id;
    private String name;
    private String provider;
    @Min(value = 0, message = "商品价格最小值不能小于0")
    private BigDecimal priceMin;
    @Min(value = 0, message = "商品价格最大值不能小于0")
    private BigDecimal priceMax;
    @Min(value = 0, message = "商品销量最小值不能小于0")
    private Integer scaleMin;
    @Min(value = 0, message = "商品销量最大值不能小于0")
    private Integer scaleMax;
    @Min(value = 0, message = "商品库存最小值不能小于0")
    private Integer stockMin;
    @Min(value = 0, message = "商品库存最大值不能小于0")
    private Integer stockMax;
    private String imgPath;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Past(message = "商品创建时间起始时间不能大于当前时间")
    private Date createDateMin;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Past(message = "商品创建时间截止时间不能大于当前时间")
    private Date createDateMax;
}
