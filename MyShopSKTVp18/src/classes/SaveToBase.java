package classes;

import entity.Buyer;
import entity.History;
import entity.Product;
import interfaces.Saveble;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author lenovo
 */
public class SaveToBase implements Saveble{
    EntityManager em;
    EntityTransaction tx;

public SaveToBase() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("MyShopSKTVp18PU");
    em = emf.createEntityManager();
    tx = em.getTransaction();
}

    @Override
    public void saveProducts(List<Product>listProducts) {
        List<Product>listProductsSaved = loadProducts();

    for(int i=0; i<listProducts.size();i++){
        if(listProductsSaved.contains(listProducts.get(i))
                && !listProductsSaved.get(i).equals(listProducts.get(i))){
                tx.begin();
                em.merge(listProducts.get(i));
                tx.commit();
        }else if(listProducts.get(i).getId() == null){
                tx.begin();
                em.persist(listProducts.get(i));
                tx.commit();
        }else{
            continue;
            }
        }
    } 

    @Override
    public List<Product>loadProducts(){
       return em.createQuery("SELECT p FROM Product p")
               .getResultList();
}

    @Override
    public void saveBuyers(List<Buyer> listBuyers){
        List<Buyer> listBuyersSaved = loadBuyers();
           tx.begin();
            for(int i=0; i<listBuyers.size();i++){
                if(listBuyersSaved.contains(listBuyers.get(i))
                        && !listBuyersSaved.get(i).equals(listBuyers.get(i))){
                    em.merge(listBuyers.get(i));
                }else{
                    em.persist(listBuyers.get(i));
                }
            }
        tx.commit();
    }

    @Override
    public List<Buyer>loadBuyers(){
       return em.createQuery("SELECT a FROM Buyer a").getResultList();
}

    @Override
    public void saveHistorys(List<History>listHistorys) {
         for(History delHistory : listHistorys){
            int flag = 0;
            for(int i=0;i<listHistorys.size();i++){
                if(delHistory.getBuyer().equals(listHistorys.get(i).getBuyer())){
                    if(delHistory.getProduct().getId() == listHistorys.get(i).getProduct().getId()){
                        flag++;
                    }
                    if(flag >1){
                        listHistorys.get(i).getProduct().setCount(listHistorys.get(i).getProduct().getCount()+1);
                        listHistorys.remove(listHistorys.get(i));
                        System.out.println("Эту книгу читатель уже читал");
                        break;
                    }
                }
            }
            if(flag > 1) break;
        }
        List<History> listHistorysSaved = loadHistorys();
        History newHistory = null;
        History editHistory = null;
        History returnHistory = null;
        int i = 0;
        for(History h : listHistorys){
            if(!listHistorysSaved.contains(h) && h.getId() == null){
                newHistory = h;
                break;
            }
            if(listHistorysSaved.contains(h) && !listHistorysSaved.get(i).equals(h)){
                editHistory = h;
                break;
            }
            if(listHistorysSaved.get(i).getId() == h.getId()
                    && listHistorysSaved.get(i).getTakeOn() == null && h.getTakeOn()!=null){
                returnHistory = h;
                break;
            }
            i++;
        }
        if(newHistory != null){
            tx.begin();
            em.persist(newHistory);
            em.flush();
            em.merge(newHistory.getProduct());
            tx.commit();
        }
        if(editHistory != null){
            tx.begin();
            em.merge(editHistory);
            em.merge(editHistory.getProduct());
            tx.commit();
        }
        if(returnHistory != null){
            tx.begin();
            em.merge(returnHistory);
            em.flush();
            em.merge(returnHistory.getProduct());
            tx.commit();
        }
    }

    @Override
    public List<History> loadHistorys() {
        return em.createQuery("SELECT h FROM History h")
                .getResultList();
    }
}