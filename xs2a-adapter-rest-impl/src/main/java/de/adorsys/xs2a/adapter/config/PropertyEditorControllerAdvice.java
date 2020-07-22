package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.BookingStatus;
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
        registerCustomEditor(dataBinder, BookingStatus.class);
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
