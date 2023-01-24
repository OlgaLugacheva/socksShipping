package com.example.socksshipping.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import com.example.socksshipping.model.Color;
import com.example.socksshipping.model.SocksSize;

@Getter
public class SockShippingDto {
    @NotNull(message = "Цвет является обязательным полем")
    private Color color;
    @NotNull(message = "Размер является обязательным полем")
    private SocksSize size;
    @Size(min = 0, max = 100, message = "Содержание хлопка должно быть 0 до 100")
    private int cottonContent;
    @Positive(message = "Количество должно быть положительным числом")
    private int quantity;

}
