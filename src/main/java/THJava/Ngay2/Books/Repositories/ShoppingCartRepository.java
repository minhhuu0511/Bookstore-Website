package THJava.Ngay2.Books.Repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import THJava.Ngay2.Books.Models.Book;
import THJava.Ngay2.Books.Models.CartItem;
import THJava.Ngay2.Books.Models.User;

public interface ShoppingCartRepository extends JpaRepository<CartItem, Long>{
	@Query("SELECT b FROM CartItem b WHERE b.customer = :id")
	public List<CartItem> findItemByCustomerId(Long id);
	@Query("SELECT b FROM Book b WHERE b.isdeleted = false")
	Page<CartItem> findWithOutDelete(Pageable page);

	@Query("SELECT b FROM Book b WHERE CONCAT(b.title, ' ', b.author, ' ', b.category, ' ', b.price) LIKE %:keyword% AND b.isdeleted = false")
    public Page<CartItem> Search(Pageable page, @Param("keyword")String keyword);
	
}
