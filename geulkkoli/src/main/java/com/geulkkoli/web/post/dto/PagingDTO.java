package com.geulkkoli.web.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@ToString
public class PagingDTO {

    private List<ListDTO> list;

    private int number;

    private boolean first;

    private boolean last;

    private int totalPages;

    private int size;

    public PagingDTO(List<ListDTO> list, int number, boolean first, boolean last, int totalPages, int size) {
        this.list = list;
        this.number = number;
        this.first = first;
        this.last = last;
        this.totalPages = totalPages;
        this.size = size;
    }

    public static PagingDTO listDTOtoPagingDTO (Page<ListDTO> page) {
        return new PagingDTO(
                page.toList(),
                page.getNumber(),
                page.isFirst(),
                page.isLast(),
                page.getTotalPages(),
                page.getSize()
        );
    }
}
