package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.model.BookingStatusTO;
import de.adorsys.xs2a.adapter.model.PaymentProductTO;
import de.adorsys.xs2a.adapter.model.PaymentServiceTO;
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
        dataBinder.registerCustomEditor(BookingStatusTO.class, new ConvertingPropertyEditorAdapter(conversionService, TypeDescriptor.valueOf(BookingStatusTO.class)));
        dataBinder.registerCustomEditor(PaymentServiceTO.class, new ConvertingPropertyEditorAdapter(conversionService, TypeDescriptor.valueOf(PaymentServiceTO.class)));
        dataBinder.registerCustomEditor(PaymentProductTO.class, new ConvertingPropertyEditorAdapter(conversionService, TypeDescriptor.valueOf(PaymentProductTO.class)));
    }
}
