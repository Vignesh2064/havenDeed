package com.heavendeeds.heavendeeds.controller;
import com.heavendeeds.heavendeeds.exception.HeavenApiException;
import com.heavendeeds.heavendeeds.service.HAVEENpropertyCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class HAVEENCategoryController {

    @Autowired
    HAVEENpropertyCategoryService haveeNpropertyCategoryService;

    @GetMapping("getAllProperty")
    public ResponseEntity<?> getAllUserDetails(@RequestParam Integer pageNo, @RequestParam Integer pageCount,@RequestParam String category,@RequestParam String city) throws HeavenApiException {
        Pageable pageable = PageRequest.of(pageNo - 1, pageCount, Sort.by("createdDt").descending());
        ResponseEntity<?> response = haveeNpropertyCategoryService.findByCategory(category,city,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getPropertyTypeCount")
    public ResponseEntity<?> getPropertyType() throws HeavenApiException {
        ResponseEntity<?> response = haveeNpropertyCategoryService.propertyTypeCount();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
