package com.epam.esm.dto.tagDto;

import com.epam.esm.Dto.certificateDto.CertificateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadTag extends RepresentationModel<ReadTag> {

    private long id;
    private String tagName;
    List<CertificateDto> certificate;
}





