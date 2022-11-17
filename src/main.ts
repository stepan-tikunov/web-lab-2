import { NestFactory } from "@nestjs/core";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";
import { AppModule } from "./app/app.module";

async function bootstrap() {
	const app = await NestFactory.create(AppModule);

	const config = new DocumentBuilder()
		.setTitle("Login API")
		.setDescription("User authorization API for lab work #2")
		.setVersion("1.0")
		.addTag("users")
		.build();
	const document = SwaggerModule.createDocument(app, config);
	SwaggerModule.setup("/", app, document);

	await app.listen(5500);
}
bootstrap();
