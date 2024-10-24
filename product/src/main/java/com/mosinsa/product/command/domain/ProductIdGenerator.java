package com.mosinsa.product.command.domain;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

public class ProductIdGenerator implements IdentifierGenerator {
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		IdentifierGenerator.super.configure(type, params, serviceRegistry);
	}

	@Override
	public void registerExportables(Database database) {
		IdentifierGenerator.super.registerExportables(database);
	}

	@Override
	public void initialize(SqlStringGenerationContext context) {
		IdentifierGenerator.super.initialize(context);
	}

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return ProductId.newId();
	}

	@Override
	public boolean supportsJdbcBatchInserts() {
		return IdentifierGenerator.super.supportsJdbcBatchInserts();
	}
}
