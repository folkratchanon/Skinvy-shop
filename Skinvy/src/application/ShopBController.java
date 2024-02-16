package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Cleansing;
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

public class ShopBController implements Initializable{
	@FXML
	private TableView <Cleansing>table;
	@FXML
	private TableColumn <Cleansing,String>col_id;
	@FXML
	private TableColumn <Cleansing,String>col_name;
	@FXML
	private TableColumn <Cleansing,String>col_brand;
	@FXML
	private TableColumn<Cleansing,Integer> col_price;
	@FXML
	private TextField text_search;

	Main m = new Main();
	Cart c;
	
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		try {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Cleansing>c = session.createQuery("from Cleansing").list();
			ObservableList<Cleansing>cList = FXCollections.observableArrayList(c);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Cleansing,String>("id_c"));
			col_name.setCellValueFactory(new PropertyValueFactory<Cleansing,String>("name_c"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Cleansing,String>("brand_c"));
			col_price.setCellValueFactory(new PropertyValueFactory<Cleansing,Integer>("price_c"));
			
			table.setItems(cList);
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
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cleansing.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Cleansing>c = session.createQuery("from Cleansing").list();
			c = session.createQuery("from Cleansing c where c.id_c='"+text_search.getText()+
					"' OR c.name_c='"+text_search.getText()+
					"' OR c.brand_c='"+text_search.getText()+
					"' OR c.price_c='"+text_search.getText()+"'").list();
			
			ObservableList<Cleansing>cList = FXCollections.observableArrayList(c);
			table.setItems(FXCollections.observableArrayList(cList));
			
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
		Cleansing click = table.getSelectionModel().getSelectedItem();
		c = new Cart(click.getId_c(),click.getName_c(),click.getBrand_c(),click.getPrice_c());
	}
	
	@FXML
	public void btnCart(ActionEvent event) throws IOException{
		m.changeScene("/fxml/Cart.fxml");
	}
	@FXML
	public void btnAcne(ActionEvent event) throws IOException{
		m.changeScene("/fxml/ShopA.fxml");
	}

	@FXML
	public void btnSunscreen(ActionEvent event) throws IOException{
		m.changeScene("/fxml/ShopC.fxml");
	}
}
