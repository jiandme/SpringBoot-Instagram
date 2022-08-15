package com.han.insta.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(
		uniqueConstraints = {
				@UniqueConstraint(
						name="subscribe_uk",
						columnNames = {"toUserId", "fromUserId"}
				)
		}
)
public class Subscribe {
	

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	

	private int toUserId;
	
	@JoinColumn(name = "fromUserId", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private User fromUser;
	
	
	@CreationTimestamp 
	private Timestamp createDate;
	
	// JPQL X
	public Subscribe(int toUserId, User fromUser) {
		this.toUserId = toUserId;
		this.fromUser = fromUser;
	}
	


	

}
