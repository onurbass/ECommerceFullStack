package com.ecommerce.util.common;

import lombok.Data;

@Data
public class PageableRequest {

    private int pageNumber;

    private int pageSize;

    private String sortableColumn;

}
