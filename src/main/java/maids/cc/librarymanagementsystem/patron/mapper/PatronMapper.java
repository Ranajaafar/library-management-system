package maids.cc.librarymanagementsystem.patron.mapper;

import maids.cc.librarymanagementsystem.patron.entity.Patron;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatronMapper {
        PatronDto toDto(Patron patron);

        Patron toPatron(PatronRequest patronRequest);

        List<PatronDto> toDtoList(List<Patron> patronList);
}
