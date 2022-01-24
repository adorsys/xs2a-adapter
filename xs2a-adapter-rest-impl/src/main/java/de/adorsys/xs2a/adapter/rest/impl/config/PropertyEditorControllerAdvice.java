/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.rest.impl.config;

import de.adorsys.xs2a.adapter.api.model.BookingStatusCard;
import de.adorsys.xs2a.adapter.api.model.BookingStatusGeneric;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.ConvertingPropertyEditorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class PropertyEditorControllerAdvice {

    private final ConversionService conversionService;

    public PropertyEditorControllerAdvice(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @InitBinder
    void initBinder(WebDataBinder dataBinder) {
        registerCustomEditor(dataBinder, BookingStatusGeneric.class);
        registerCustomEditor(dataBinder, BookingStatusCard.class);
        registerCustomEditor(dataBinder, PaymentService.class);
        registerCustomEditor(dataBinder, PaymentProduct.class);
    }

    private <T> void registerCustomEditor(WebDataBinder dataBinder, Class<T> type) {
        dataBinder.registerCustomEditor(type,
            new StrictConvertingPropertyEditorAdapter(conversionService, TypeDescriptor.valueOf(type)));
    }
}

class StrictConvertingPropertyEditorAdapter extends ConvertingPropertyEditorAdapter {

    private final ConversionService conversionService;
    private final TypeDescriptor targetDescriptor;

    StrictConvertingPropertyEditorAdapter(ConversionService conversionService, TypeDescriptor targetDescriptor) {
        super(conversionService, targetDescriptor);
        this.conversionService = conversionService;
        this.targetDescriptor = targetDescriptor;
    }

    @Override
    public void setAsText(String text) {
        Object value = this.conversionService.convert(text, TypeDescriptor.valueOf(String.class), this.targetDescriptor);
        if (value == null) {
            throw new IllegalArgumentException(text);
        }
        setValue(value);
    }
}
