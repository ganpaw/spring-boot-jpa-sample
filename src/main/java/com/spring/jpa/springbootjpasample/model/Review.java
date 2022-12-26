package com.spring.jpa.springbootjpasample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.cache.CacheType;

import javax.persistence.*;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String comment;

	// The default depends on the cardinality of the relationship.
	// All to-one relationships use FetchType.EAGER and all to-many relationships FetchType.LAZY.
	// In below case, we don't want to fetch book as soon as we lookup review. further when we call review.getBook, Hibernate
	// should do another select to retrieve book. Hence we explicitly used LAZY loading.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "book_id", nullable = false)
	//https://www.concretepage.com/hibernate/example-ondelete-hibernate
	// https://rogerkeays.com/jpa-cascadetype-remove-vs-hibernate-ondelete
	@OnDelete(action = OnDeleteAction.CASCADE)

	@JsonIgnore // to ignore below field since we are using fetch = FetchType.LAZY explicitly
	/**
	 * Below response will not contain book
	 * [
	 *     {
	 *         "id": 1,
	 *         "comment": "A great java book",
	 *     }
	 * ]
	 * If we don't provide @JsonIgnore along with Lazy. On "return new ResponseEntity<>(reviews, HttpStatus.OK);"
	 * we will get below since Book instance is empty/null.
	 * {
	 *     "timestamp": "2022-12-26T02:12:15.595+00:00",
	 *     "status": 500,
	 *     "error": "Internal Server Error",
	 *     "message": "Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor];
	 *     nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class
	 *     org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer
	 *     (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
	 *     (through reference chain: java.util.ArrayList[0]->com.spring.jpa.springbootjpasample.model.Review[\"book\"]->com.spring.jpa.springbootjpasample.model.Book$HibernateProxy$VyEXFzhj[\"hibernateLazyInitializer\"])",
	 *     "path": "/api/books/1/reviews"
	 * }
	 */

	private Book book;

	public Long getId() {
		return id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
