import { Injectable } from "@nestjs/common";
import * as jwt from "jsonwebtoken";
import { UserDto } from "src/users/types.dto";

@Injectable()
export class JwtService {
	public issue(user: UserDto, secret: string): string {
		return jwt.sign({ username: user.username }, secret);
	}

	public verify(token: string, secret: string): UserDto {
		let result: UserDto = null;

		jwt.verify(token, secret, (err, authData) => {
			if (!err) {
				result = {
					username: authData["username"]
				};
			}
		});

		return result;
	}
}
