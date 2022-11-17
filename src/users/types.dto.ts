export class UserDto {
	public username: string;
}

export class PasswordUserDto extends UserDto {
	public password: string;
}

export class TokenUserDto extends UserDto {
	public token: string;
}

export class TokenDto {
	public token: string;
}
