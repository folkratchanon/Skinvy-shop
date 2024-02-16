package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hql.entity.Cart;
import com.hql.entity.Invoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.control.TableColumn;

public class CartController implements Initializable{
	@FXML
	private TableView<Cart> table;
	@FXML
	private TableColumn<Cart,String> col_id;
	@FXML
	private TableColumn<Cart,String> col_name;
	@FXML
	private TableColumn<Cart,String> col_brand;
	@FXML
	private TableColumn<Cart,Integer> col_price;
	@FXML
	private Label label_total;

	Image img = new Image("/pic/LogoB.png");
	Main m = new Main();
	Cart c;
	@Override
	public void initialize(URL url,ResourceBundle rb) {
		try {
			SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cart.class).buildSessionFactory();
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			List<Cart>c = session.createQuery("from Cart").list();
			ObservableList<Cart>cList = FXCollections.observableArrayList(c);
			session.getTransaction().commit();
			
			col_id.setCellValueFactory(new PropertyValueFactory<Cart,String>("id_cart"));
			col_name.setCellValueFactory(new PropertyValueFactory<Cart,String>("name_cart"));
			col_brand.setCellValueFactory(new PropertyValueFactory<Cart,String>("brand_cart"));
			col_price.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("price_cart"));
			
			table.setItems(cList);
			setTotal();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	@FXML
	public void btnPurchase(ActionEvent event) throws IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Invoice.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
				
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Invoice inv = new Invoice(Integer.parseInt(label_total.getText()));

			session.save(inv);
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			Alert a = new Alert(Alert.AlertType.NONE,"ชำระเงินสำเร็จ",ButtonType.OK);
			a.show();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Invoice.fxml"));
			Stage stg = new Stage();
			stg.setScene(new Scene(root));
			stg.getIcons().add(img);
			stg.setTitle("Skinvy");
			stg.show();
			initialize(null,null);
			factory.close();
		}
	}
	@FXML
	public void btnDelete(ActionEvent event) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cart.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
	
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			Cart cart = session.get(Cart.class,c.getId_cart());
			//delete
			session.delete(cart);
			
			//commit transaction
			session.getTransaction().commit();
		}finally {
			Alert a = new Alert(Alert.AlertType.NONE,"ลบ "+c.getName_cart()+" ออกจากตะกร้าสำเร็จ",ButtonType.OK);
			a.show();
			initialize(null,null);
			factory.close();
		}
	}
	@FXML
	public void refresh(MouseEvent event) {
		initialize(null, null);
	}

	@FXML
	public void rowClick(MouseEvent event) {
		Cart click = table.getSelectionModel().getSelectedItem();
		c = new Cart(click.getId_cart(),click.getName_cart(),click.getBrand_cart(),click.getPrice_cart());
	}
	public void setTotal() throws IOException {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Cart.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			//start a transaction
			session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Cart>cart = session.createQuery("from Cart").list();
			
			int sum = 0;
			for(Cart c : cart) {
				 sum += c.getPrice_cart();
				 System.out.println(c);
			}
			
			label_total.setText(String.valueOf(sum));

			//commit transaction
			session.getTransaction().commit();
		}finally {
			initialize(null,null);
			factory.close();
		}
	}
	@FXML
	public void btnAcne(ActionEvent event) throws IOException{
		m.changeScene("/fxml/ShopA.fxml");
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
