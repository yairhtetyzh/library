package com.book.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.book.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>{

	@Query(value = "SELECT * FROM book b "
			+ "WHERE (b.isbn_number = :isbnNumber) "
			, nativeQuery = true)
	List<Book> findAllBookByISBNNumber(@Param("isbnNumber") String isbnNumber);

}
