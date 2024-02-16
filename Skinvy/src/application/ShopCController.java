package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Sunscreen;
import com.hql.entity.Cart;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

public class ShopCController implements Initializable{
	@FXML
	private TableView <Sunscreen>table;
	@FXML
	private TableColumn <Sunscreen,String>col_id;
	@FXML
	private TableColumn <Sunscreen,String>col_name;
	@FXML
	private TableColumn<Sunscreen,String> col_brand;
	@FXML
	private TableColumn <Sunscreen,Integer>col_price;
	@FXML
	private TextField text_search;

	Main m = new Main();
	Cart c;
	
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		try {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Sunscreen>s = session.createQuery("from Sunscreen").list();
			ObservableList<Sunscreen>sList = FXCollections.observableArrayList(s);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Sunscreen,String>("id_s"));
			col_name.setCellValueFactory(new PropertyValueFactory<Sunscreen,String>("name_s"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Sunscreen,String>("brand_s"));
			col_price.setCellValueFactory(new PropertyValueFactory<Sunscreen,Integer>("price_s"));
			
			table.setItems(sList);
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	@FXML
	public void btnAddCart(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cart.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			session.save(c);
		
			//commit transaction
			session.getTransaction().commit();
		}finally {
			Alert a = new Alert(Alert.AlertType.NONE,"เพิ่ม "+c.getName_cart()+" เข้าสู่ตะกร้าสำเร็จ",ButtonType.OK);
			a.show();
			initialize(null,null);
			factory.close();
		}
	}

	@FXML
	public void btnSearch(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Sunscreen.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Sunscreen>s = session.createQuery("from Sunscreen").list();
			s= session.createQuery("from Sunscreen c where c.id_s='"+text_search.getText()+
					"' OR c.name_s='"+text_search.getText()+
					"' OR c.brand_s='"+text_search.getText()+
					"' OR c.price_s='"+text_search.getText()+"'").list();
			
			ObservableList<Sunscreen>sList = FXCollections.observableArrayList(s);
			table.setItems(FXCollections.observableArrayList(sList));
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

	@FXML
	public void refresh(MouseEvent event) {
		initialize(null, null);
	}

	@FXML
	public void rowClick(MouseEvent event) {
		Sunscreen click = table.getSelectionModel().getSelectedItem();
		c = new Cart(click.getId_s(),click.getName_s(),click.getBrand_s(),click.getPrice_s());
	}
	
	@FXML
	public void btnCart(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Cart.fxml");
	}
	@FXML
	public void btnCleansing(ActionEvent event) throws IOException{
		m.changeScene("/fxml/ShopB.fxml");
	}

	@FXML
	public void btnAcne(ActionEvent event) throws IOException{
		m.changeScene("/fxml/ShopA.fxml");
	}
}
