package interfaces;

import entity.Buyer;
import entity.History;
import entity.Product;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface Saveble {
public void saveProducts(List<Product> listProducts);
public List<Product> loadProducts();
public void saveBuyers(List<Buyer> listBuyers);
public List<Buyer> loadBuyers();
public void saveHistorys(List<History> listHistorys);
public List<History> loadHistorys();
} 
