package com.mosinsa.product.command;

import com.mosinsa.product.command.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class Data {

	@Autowired
	ProductRepository repository;

//	@Test
//	@Transactional
//	@Commit
	void test(){
		List<Product> all = repository.findAll();

		Random random = new Random();
		for (Product product : all) {
			List<ProductOptions> productOptions = product.getProductOptions();
			ProductOptions productOptions1 = productOptions.get(0);
			if (productOptions.size()==1){
				for (ProductOptionsValue productOptionsValue : productOptions1.getProductOptionsValues()) {
					OptionCombinationValues of1 = OptionCombinationValues.of(productOptionsValue);
					OptionCombinations of = OptionCombinations.of(Stock.of(random.nextInt(50) + 10));
					of.addOptionCombinationValues(List.of(of1));
					product.addCombinations(List.of(of));
				}
			}else{
				ProductOptions productOptions2 = productOptions.get(1);

				for (ProductOptionsValue productOptionsValue : productOptions1.getProductOptionsValues()) {
					for (ProductOptionsValue optionsValue : productOptions2.getProductOptionsValues()) {
						OptionCombinations of = OptionCombinations.of(Stock.of(random.nextInt(100) + 13));

						of.addOptionCombinationValues(List.of(OptionCombinationValues.of(productOptionsValue),OptionCombinationValues.of(optionsValue)));

						product.addCombinations(List.of(of));
					}
				}
			}

		}
	}

}
