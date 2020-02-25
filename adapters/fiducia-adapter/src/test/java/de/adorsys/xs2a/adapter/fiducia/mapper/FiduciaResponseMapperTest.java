package de.adorsys.xs2a.adapter.fiducia.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class FiduciaResponseMapperTest {

    private final FiduciaResponseMapper mapper = Mappers.getMapper(FiduciaResponseMapper.class);

    @Test
    void toListOfStringsReturnsNullForNullInput() {
        assertNull(mapper.toListOfStrings(null));
    }

    @Test
    void toListOfStringsCreatesListWithOneItem() {
        assertThat(mapper.toListOfStrings("asdf")).containsExactly("asdf");
    }
}
