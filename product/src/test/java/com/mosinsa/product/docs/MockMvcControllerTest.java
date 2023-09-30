package com.mosinsa.product.docs;


import com.mosinsa.product.controller.ProductController;
import com.mosinsa.product.db.dto.ProductDto;
import com.mosinsa.product.db.entity.DiscountPolicy;
import com.mosinsa.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mosinsa.product.docs.ApiDocumentUtils.getDocumentRequest;
import static com.mosinsa.product.docs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class MockMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    void getProduct() throws Exception {
        String productId = "05e385c2-1b02-4ae9-bc07-49811493c504";
        ProductDto productDto = new ProductDto("05e385c2-1b02-4ae9-bc07-49811493c504","test_product2",
                10000,999999999, DiscountPolicy.NONE,0,0);
        given(service.findProductById(productId)).willReturn(productDto);

        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
                .andDo(document("get-single-product",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("productId")
                        ),
                        responseFields(
                                fieldWithPath("productId").type(JsonFieldType.STRING).description("productId"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("price"),
                                fieldWithPath("stock").type(JsonFieldType.NUMBER).description("stock"),
                                fieldWithPath("discountPolicy").type(JsonFieldType.STRING).description("discountPolicy"),
                                fieldWithPath("discountPrice").type(JsonFieldType.NUMBER).description("discountPrice"),
                                fieldWithPath("likes").type(JsonFieldType.NUMBER).description("likes")
                       )
                ));
    }

//    @Test
    void getProducts() throws Exception {

        List<ProductDto> productDtos = Arrays.asList(
                new ProductDto("05e385c2-1b02-4ae9-bc07-49811493c504","test_product2",
                        10000,999999999, DiscountPolicy.NONE,0,0)
        );
        PageImpl<ProductDto> value = new PageImpl<ProductDto>(productDtos);

        PageRequest of = PageRequest.of(0, 10);
        given(service.findAllProducts(any(Pageable.class))).willReturn(value);

        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/products")
                        .param("page","0")
                        .param("size","10")
                        .accept(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk())
                .andDo(document("get-products",
                        getDocumentRequest(),
                        getDocumentResponse(),

                        responseFields(
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("content.[].productId").type(JsonFieldType.STRING).description("productId"),
                                fieldWithPath("content.[].name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("content.[].price").type(JsonFieldType.NUMBER).description("price"),
                                fieldWithPath("content.[].stock").type(JsonFieldType.NUMBER).description("stock"),
                                fieldWithPath("content.[].discountPolicy").type(JsonFieldType.STRING).description("discountPolicy"),
                                fieldWithPath("content.[].discountPrice").type(JsonFieldType.NUMBER).description("discountPrice"),
                                fieldWithPath("content.[].likes").type(JsonFieldType.NUMBER).description("likes")
                        )
                ));

    }

}
