package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Acne;
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

public class ShopAController implements Initializable{
	@FXML
	private TableView <Acne>table;
	@FXML
	private TableColumn<Acne,String> col_id;
	@FXML
	private TableColumn<Acne,String>col_name;
	@FXML
	private TableColumn<Acne,String> col_brand;
	@FXML
	private TableColumn <Acne,Integer>col_price;
	@FXML
	private TextField text_search;

	Main m = new Main();
	Cart c;
	
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		try {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Acne>a = session.createQuery("from Acne").list();
			ObservableList<Acne>aList = FXCollections.observableArrayList(a);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Acne,String>("id_a"));
			col_name.setCellValueFactory(new PropertyValueFactory<Acne,String>("name_a"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Acne,String>("brand_a"));
			col_price.setCellValueFactory(new PropertyValueFactory<Acne,Integer>("price_a"));
			
			table.setItems(aList);
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Acne.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Acne>a = session.createQuery("from Acne").list();
			a = session.createQuery("from Acne c where c.id_a='"+text_search.getText()+
					"' OR c.name_a='"+text_search.getText()+
					"' OR c.brand_a='"+text_search.getText()+
					"' OR c.price_a='"+text_search.getText()+"'").list();
			
			ObservableList<Acne>aList = FXCollections.observableArrayList(a);
			table.setItems(FXCollections.observableArrayList(aList));
			
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
		Acne click = table.getSelectionModel().getSelectedItem();
		c = new Cart(click.getId_a(),click.getName_a(),click.getBrand_a(),click.getPrice_a());
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
	public void btnSunscreen(ActionEvent event) throws IOException{
		m.changeScene("/fxml/ShopC.fxml");
	}
}
