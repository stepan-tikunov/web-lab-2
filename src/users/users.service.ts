import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { PasswordUserDto } from "./types.dto";
import { User } from "./user.entity";
import * as crypto from "crypto";

@Injectable()
export class UsersService {
	constructor(
		@InjectRepository(User)
		private repository: Repository<User>
	) {}

	public async findOne(username: string): Promise<User> {
		return this.repository.findOneBy({ username });
	}

	async hash(password: string): Promise<string> {
		return new Promise((resolve, reject) => {
			// generate random 16 bytes long salt
			const salt = crypto.randomBytes(16).toString("hex");

			crypto.scrypt(password, salt, 64, (err, derivedKey) => {
				if (err) reject(err);
				resolve(salt + ":" + derivedKey.toString("hex"));
			});
		});
	}

	async verify(password, hash): Promise<boolean> {
		return new Promise((resolve, reject) => {
			const [salt, key] = hash.split(":");
			crypto.scrypt(password, salt, 64, (err, derivedKey) => {
				if (err) reject(err);
				resolve(key == derivedKey.toString("hex"));
			});
		});
	}

	public async create(createUserDto: PasswordUserDto): Promise<User> {
		createUserDto.password = await this.hash(createUserDto.password);

		const user = this.repository.create(createUserDto);

		try {
			await this.repository.insert(user);
			return user;
		} catch (e) {
			return null;
		}
	}
}
