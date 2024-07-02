package THJava.Ngay2.Books.APIControllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import THJava.Ngay2.Books.Models.Book;
import THJava.Ngay2.Books.Services.BookServices;

@RestController
@RequestMapping("/API/books")
public class APIBookController {
	@Autowired
	private BookServices bookServices;
	private BookServices bookServices2;

//	@GetMapping("/page/{pageNum}")
//	public List<Book> list(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
//			@Param("sortType") String sortType, @Param("keyword") String keyword) {
//		Page<Book> page = bookServices.listAllWithOutDelete(pageNum, sortField, sortType, keyword);
//		return page.getContent();
//	}
	
	
//	@GetMapping("/")
//
//	public ResponseEntity<Book> viewAllBook(Model model) {
//		List<Book> listBook = bookServices.listAllWithOutDelete();
//		model.addAttribute("books", listBook);
//		return page.getContent();
//	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> get(@PathVariable(name = "id") Integer id) {
		System.out.println("get1");
		try {
			Book book = bookServices.get(id);
			if (book == null) {
				return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/add")
	public void add(@RequestBody Book book) {
		System.out.println("add");
	    bookServices.save(book);
	}
	@PutMapping("/edit/{id}")
	public ResponseEntity<?> update(@RequestBody Book book, @PathVariable(name = "id") Long id) {
		System.out.println("edit");
		try {
	    	Book Foundbook = bookServices.get(id);
	    	if (Foundbook == null) {
				return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
			}
	    	book.setId(id);
	    	bookServices.save(book);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable(name = "id") Integer id) {
		System.out.println("Delete");
		bookServices.delete(id);
	}
}


