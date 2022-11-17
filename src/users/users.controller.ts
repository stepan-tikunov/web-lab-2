import {
	Body,
	Controller,
	HttpException,
	HttpStatus,
	Post
} from "@nestjs/common";
import { ConfigService } from "src/config/config.service";
import { JwtService } from "src/jwt/jwt.service";
import { PasswordUserDto, TokenDto, TokenUserDto, UserDto } from "./types.dto";
import { UsersService } from "./users.service";

@Controller("users")
export class UsersController {
	constructor(
		private readonly usersService: UsersService,
		private readonly jwtService: JwtService,
		private readonly configService: ConfigService
	) {}

	@Post("create")
	async createUser(
		@Body() createUserDto: PasswordUserDto
	): Promise<TokenUserDto> {
		const secret = this.configService.getValue("JWT_SECRET");
		const user = await this.usersService.create(createUserDto);
		if (!user) {
			throw new HttpException(
				"REGISTER_USERNAME_ERROR",
				HttpStatus.BAD_REQUEST
			);
		}
		const token = await this.jwtService.issue(
			{ username: user.username },
			secret
		);

		return { username: user.username, token };
	}

	@Post("login")
	async loginUser(
		@Body() loginUserDto: PasswordUserDto
	): Promise<TokenUserDto> {
		const user = await this.usersService.findOne(loginUserDto.username);

		if (user) {
			const validPassword = await this.usersService.verify(
				loginUserDto.password,
				user.password
			);

			if (validPassword) {
				const secret = this.configService.getValue("JWT_SECRET");
				const token = await this.jwtService.issue(loginUserDto, secret);

				return {
					username: user.username,
					token
				};
			} else {
				throw new HttpException(
					"LOGIN_PASSWORD_ERROR",
					HttpStatus.FORBIDDEN
				);
			}
		} else {
			throw new HttpException(
				"LOGIN_USERNAME_ERROR",
				HttpStatus.NOT_FOUND
			);
		}
	}

	@Post("auth")
	async checkAuthorization(@Body() tokenDto: TokenDto): Promise<UserDto> {
		const secret = this.configService.getValue("JWT_SECRET");
		const user = this.jwtService.verify(tokenDto.token, secret);

		return user;
	}
}
