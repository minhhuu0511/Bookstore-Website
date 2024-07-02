package THJava.Ngay2.Books.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import THJava.Ngay2.Books.Models.Book;
import THJava.Ngay2.Books.Models.CartItem;
import THJava.Ngay2.Books.Models.CustomUserDetails;
import THJava.Ngay2.Books.Models.User;
import THJava.Ngay2.Books.Services.BookServices;
import THJava.Ngay2.Books.Services.ShoppingCartService;
import THJava.Ngay2.Books.Utils.FileUploadUtil;

@ComponentScan("THJava.Ngay2.Books")
@Controller
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

	@Autowired
	private BookServices bookServices;
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/view")
	public String viewCart(Model model) {
		model.addAttribute("all_items_in_shoppingcart",shoppingCartService.getAllCartItems());
		model.addAttribute("total_amount",shoppingCartService.listItem());
		model.addAttribute("total_price",shoppingCartService.getAmount());
		//model.addAttribute("list_item",shoppingCartService.listItemSave());
		return "/cart/shopping_cart";
	}
	
	
	@GetMapping("/add/{id}")
	public String addItem(@PathVariable("id") Long id,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 Long currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
		 
		Book book=bookServices.get(id);
		if(book!=null)
		{
			CartItem item=new CartItem();
			item.setItemId(1L);
			item.setBookId(book.getId());
			item.setAuthor(book.getAuthor());
			item.setCategory(book.getCategory());
			item.setPrice(book.getPrice());
			item.setTitle(book.getTitle());
			item.setCustomer(currentUserId);
			item.setPhotourl(book.getPhotourl());
			item.setQuantity(1);
			item.setTotalPrice(shoppingCartService.getIntoMoney(id));
//			item.setTotalPrice(book.getPrice()*book.getQuantity());
//			model.addAttribute("total_item",shoppingCartService.listItem(null))
			shoppingCartService.add(item);
			
			
			
		}
		return "redirect:/shoppingcart/view";
	}
	


	@GetMapping("/save")
	public String saveEntities() {
        shoppingCartService.saveMultipleEntities();
        shoppingCartService.clearCart();
        return "redirect:/shoppingcart/view";
    }
//	@GetMapping("/save")
//	public String saveEntities(@ModelAttribute("list_item") ArrayList<CartItem> list) {
//        shoppingCartService.saveMultipleEntities(list);
//        return "redirect:/shoppingcart/view";
//    }
	
	@GetMapping("/fail")
	public String loginfail() {
		return "/cart/error_login";
	}
	
	
	
	@GetMapping("/clear")
	public String clearCart()
	{
		shoppingCartService.clearCart();
		return "redirect:/shoppingcart/view";
	}
	
	
	@GetMapping("/remove/{bookId}")
	public String removeItem(@PathVariable("bookId") Long id) {
		shoppingCartService.remove(id);
		return "redirect:/shoppingcart/view";
	}
	
	@PostMapping("/update")
	public String updateItem(@RequestParam("bookId") Long bookId,@RequestParam("quantity") int quantity) {
		shoppingCartService.update(bookId, quantity);
		return "redirect:/shoppingcart/view";
	}
	
	
	@GetMapping("/pagehistory/{pageNum}")
	public String viewAllBook(Model model, @PathVariable(name = "pageNum") int pageNum,
			@Param("sortField") String sortField, @Param("sortType") String sortType,
			@Param("keyword") String keyword) {
		sortField = sortField==null?"id":sortField;
		sortType = sortType==null?"asc":sortType;
		Page<Book> page = bookServices.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
		List<Book> listBook = page.getContent();
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortType", sortType);
		model.addAttribute("reverseSortType", sortType.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		model.addAttribute("books", listBook);
		return "home/index";
	}
	
//	@GetMapping
//	public String viewHomePage(Model model) {
//		return viewAllBook(model, 1, "id", "asc", " ");
//	}
	
	@GetMapping("/history")
	public String historyPurchase(Model model)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();
		List<CartItem> listUser = shoppingCartService.findItemByCustomerId(currentUserId);
		model.addAttribute("cartitems", listUser);
		
		return "/cart/history";
	}
}
