package THJava.Ngay2.Books.Models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "cartitem")
public class CartItem {
	@Id
	@Column(name = "cart_item_id")

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;
	
//	@ManyToOne
//	@JoinColumn(name = "id", nullable = false)
	@Column(name="book_id")
	private Long bookId;
	
	
	
	@Column(nullable = true, length = 255)
	private String photourl;
	@Transient
	public String getPhotosImagePath() {
		if (photourl == null || itemId == null)
			return null;

		return "/photos/" + bookId + "/" + photourl;
	}
	
	
	public String getPhotourl() {
		return photourl;
	}


	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	

	@Column(name = "title", nullable = false, length = 255)
	private String title;
	@Column(name = "author", nullable = false, length = 255)
	private String author;
	@Column(name = "price")
	private Long price;
	
	
	
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	@Column(name="quantity")
	private int quantity;
	@Column(name="toltal_price")
	private double totalPrice;
	
//	@ManyToOne
//	@JoinColumn(name = "user_id", nullable = false)
	@Column(name="customer_id")
	private Long customer;

	public Long getBookId() {
		return bookId;
	}


	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}


	public Long getCustomer() {
		return customer;
	}


	public void setCustomer(Long customer) {
		this.customer = customer;
	}


	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public CartItem() {
		super();
	}

	
	
}
