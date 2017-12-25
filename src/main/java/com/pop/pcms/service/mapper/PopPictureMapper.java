package com.pop.pcms.service.mapper;

import com.pop.pcms.domain.*;
import com.pop.pcms.service.dto.PopPictureDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PopPicture and its DTO PopPictureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PopPictureMapper {

    @Mapping(source = "contribute.id", target = "contributeId")
    PopPictureDTO popPictureToPopPictureDTO(PopPicture popPicture);

    List<PopPictureDTO> popPicturesToPopPictureDTOs(List<PopPicture> popPictures);

    @Mapping(source = "contributeId", target = "contribute")
    PopPicture popPictureDTOToPopPicture(PopPictureDTO popPictureDTO);

    List<PopPicture> popPictureDTOsToPopPictures(List<PopPictureDTO> popPictureDTOs);

    default PopContribute popContributeFromId(Long id) {
        if (id == null) {
            return null;
        }
        PopContribute popContribute = new PopContribute();
        popContribute.setId(id);
        return popContribute;
    }
}
