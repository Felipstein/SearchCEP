package br.felipstein.searchcep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	
	private Map<String, TextField> fields;
	private List<Node> nodes;
	
	@FXML
	private TextField cep, localText, logText, ufText, districtText, complementText, dddText, giaText, ibgeText, siafiText;
	@FXML
	private Label localLabel, logLabel, ufLabel, districtLabel, complementLabel, dddLabel, giaLabel, ibgeLabel, siafiLabel;
	
	public void onSearch(ActionEvent event) {
		if(nodes == null) {
			this.loadNodes();
		}
		try {
			this.verifyCEP(cep.getText());
		} catch(AlertException e) {
			System.out.println("[" + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(System.currentTimeMillis()) + "] [ERRO] " + e.getMessage());
			e.showDialog();
			return;
		}
		try {
			URL url = new URL("https://viacep.com.br/ws/" + cep.getText().trim() + "/json/");
			System.out.println("Abrindo conexão com " + url.getAuthority() + "...");
			URLConnection connection = url.openConnection();
			System.out.println("Conectado com êxito.");
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			System.out.println("Fazendo leitura e conversão para JSON Object");
			StringBuilder sb = new StringBuilder();
			reader.lines().forEach(l -> sb.append(l));
			JSONObject json = new JSONObject(sb.toString());
			this.fields.forEach((jsonKey, field) -> field.setText(json.getString(jsonKey)));
			this.nodes.forEach(node -> node.setDisable(false));
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(SearchCEP.getInstance().getStage());
			alert.setTitle("ERRO.");
			alert.setHeaderText("Aconteceu algum erro enquanto era feito o processo de busca.");
			alert.setContentText("Dados do erro: " + e.getMessage() + ", Causa: " + e.getClass().getName().replace(" class", ""));
			e.printStackTrace();
		}
	}
	
	public void onClear(ActionEvent e) {
		if(nodes == null) {
			this.loadNodes();
		}
		this.fields.values().forEach(field -> field.setText(""));
		this.nodes.forEach(node -> node.setDisable(true));
	}
	
	public void verifyCEP(String cep) throws AlertException {
		if(cep.isEmpty()) {
			throw new AlertException("Formato inválido", "O CEP está em branco.", "Coloque algum código de endereçamento postal no formato válido.");
		}
		if(!cep.contains("-")) {
			throw new AlertException("Formato inválido", "O CEP não está específicado corretamente.", "Coloque algum código de endereçamento postal no formato válido.");
		}
		try {
			Integer.parseInt(cep.replace("-", "").trim());
		} catch(NumberFormatException e) {
			throw new AlertException("Formato inválido", "O CEP apresenta letras.", "Coloque algum código de endereçamento postal no formato válido.");
		}
	}
	
	private void loadNodes() {
		this.fields = new HashMap<>();
		this.fields.put("localidade", localText);
		this.fields.put("logradouro", logText);
		this.fields.put("uf", ufText);
		this.fields.put("bairro", districtText);
		this.fields.put("complemento", complementText);
		this.fields.put("ddd", dddText);
		this.fields.put("gia", giaText);
		this.fields.put("ibge", ibgeText);
		this.fields.put("siafi", siafiText);
		this.nodes = new ArrayList<>();
		this.nodes.addAll(fields.values());
		this.nodes.add(localLabel);
		this.nodes.add(logLabel);
		this.nodes.add(ufLabel);
		this.nodes.add(districtLabel);
		this.nodes.add(complementLabel);
		this.nodes.add(dddLabel);
		this.nodes.add(giaLabel);
		this.nodes.add(ibgeLabel);
		this.nodes.add(siafiLabel);
	}
	
}