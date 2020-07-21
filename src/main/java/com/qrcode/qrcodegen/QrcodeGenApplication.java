package com.qrcode.qrcodegen;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

import static org.springframework.http.ResponseEntity.ok;

@SpringBootApplication
public class QrcodeGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrcodeGenApplication.class, args);
	}
	@Bean
	public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
		return new BufferedImageHttpMessageConverter();
	}
}

@RestController
class QRCodeController{

	@GetMapping(value="/genqr/{str}",produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<BufferedImage> generateqr(@PathVariable String str) throws Exception{
		return ok().body(genQRCode(str));
	}

	BufferedImage genQRCode(String str) throws Exception {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix =
				barcodeWriter.encode(str, BarcodeFormat.QR_CODE, 250, 250);

		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}



}