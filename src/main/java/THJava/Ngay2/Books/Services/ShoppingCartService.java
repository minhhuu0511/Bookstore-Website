package THJava.Ngay2.Books.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import THJava.Ngay2.Books.Models.Book;
import THJava.Ngay2.Books.Models.CartItem;
import THJava.Ngay2.Books.Repositories.ShoppingCartRepository;

@Service
@Transactional
public class ShoppingCartService {

	Map<Long, CartItem> shoppingCart = new HashMap<>();
	List<CartItem> listitem=new ArrayList<>();
	
	public void clearCart()
	{
//		listitem.clear();
		shoppingCart.clear();
	}
	

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	int pageSize = 5;

	public Page<CartItem> listAll(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		return shoppingCartRepository.findAll(pageable);
	}

	

	public Page<CartItem> listAllWithOutDelete(int pageNum, String sortField, String sortType, String keyword) {
		Pageable pageable = PageRequest.of(pageNum - 1, 3,
				sortType.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		System.out.println(keyword);
		if (keyword != null) {
			return shoppingCartRepository.Search(pageable, keyword);
		}
		return shoppingCartRepository.findWithOutDelete(pageable);

	}
	
	
	
	public void add(CartItem newItem) {
		CartItem cartItem = shoppingCart.get(newItem.getBookId());
		if (cartItem == null) {
			shoppingCart.put(newItem.getBookId(), newItem);
			listitem.add(newItem);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + 1);
			
		}
	}


	public void remove(Long id) {
		shoppingCart.remove(id);
	}

	public void clear() {
		shoppingCart.clear();
	}

	public Collection<CartItem> getAllCartItems() {
		return shoppingCart.values();
	}

	public int getCount() {
		return shoppingCart.size();
	}

	public double getAmount() {
		return shoppingCart.values().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
	}

	public double getIntoMoney(Long id) {
		Set<Map.Entry<Long, CartItem>> entrySet = shoppingCart.entrySet();
		Optional<CartItem> result = entrySet.stream()
			    .filter(entry -> entry.getKey().equals(id))
			    .map(Map.Entry::getValue)
			    .findFirst();
		if (result.isPresent()) {
		    CartItem value = result.get();
		    return value.getPrice()*value.getQuantity();
		} else {

			return 0l;
		}
	}



	public void save(CartItem product) {
		shoppingCartRepository.save(product);
	}

	public CartItem get(Long id) {
		return shoppingCartRepository.findById(id).orElse(null);
	}

	public void delete(Long id) {
		shoppingCartRepository.deleteById(id);
	}

	public CartItem update(Long itemid, int quantity) {
		CartItem cartItem = shoppingCart.get(itemid);
		cartItem.setQuantity(quantity);
		return cartItem;
	}
	
	
	//tra ve 1 list de add vao db
//	public ArrayList<CartItem> listItemSave(){
//		
//		Set<Map.Entry<Long, CartItem>> entrySet = shoppingCart.entrySet();
//		ArrayList<CartItem> resultList = (ArrayList<CartItem>) entrySet.stream()
//			    .map(Map.Entry::getValue)
//			    .collect(Collectors.toList());
//		return resultList;
//	}

	
	
	//ArrayList<CartItem> list=listItemSave();
	
	public void saveMultipleEntities() {
		for (Map.Entry<Long, CartItem> entry : shoppingCart.entrySet()) {
		    Long key = entry.getKey();
		    CartItem value = entry.getValue();
		   shoppingCartRepository.save(value);
		}
//		shoppingCart.forEach((key, value) -> {
//		   shoppingCartRepository.save(value);
//		});     
    }
	
	
	//tra ve danh sach doi tuong
	public int listItem(){
		int sl=0;
		Set<Map.Entry<Long, CartItem>> entrySet = shoppingCart.entrySet();
		List<CartItem> resultList = entrySet.stream()
			    .map(Map.Entry::getValue)
			    .collect(Collectors.toList());

		// Sử dụng danh sách đối tượng resultList
		for (CartItem obj : resultList) {
		    // Làm gì đó với đối tượng obj
			sl=sl+obj.getQuantity();
			
		}
		return sl;	
	}
	
	
	
	public List<CartItem> findItemByCustomerId(Long id)
	{
		List<CartItem> list=shoppingCartRepository.findItemByCustomerId(id);
		
		return list;
		
	}
}
