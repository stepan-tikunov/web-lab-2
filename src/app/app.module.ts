import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ConfigService } from "../config/config.service";
import { UsersModule } from "../users/users.module";
import * as dotenv from "dotenv";

dotenv.config();
const configService = new ConfigService();

@Module({
	imports: [
		TypeOrmModule.forRoot(configService.getTypeOrmConfig()),
		UsersModule
	]
})
export class AppModule {}
