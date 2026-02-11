package com.bookservice.common.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		//현재 트랜잭션이 read-only인지 확인
		boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
		//readOnly이면 SLAVE 반환, 아니면 MASTER 반환
		return isReadOnly ? DataSourceType.SLAVE : DataSourceType.MASTER;
	}
}
