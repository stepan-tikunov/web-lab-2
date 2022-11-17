import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { ConfigService } from "src/config/config.service";
import { JwtService } from "src/jwt/jwt.service";
import { User } from "./user.entity";
import { UsersController } from "./users.controller";
import { UsersService } from "./users.service";

@Module({
	imports: [TypeOrmModule.forFeature([User])],
	providers: [UsersService, JwtService, ConfigService],
	controllers: [UsersController]
})
export class UsersModule {}
