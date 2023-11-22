CREATE TABLE chat_user
(
	email varchar(100) unique not null primary key,
	user_name varchar(100) not null,
	password varchar(100) not null,
	active bool not null,
	image_path text null,
	created_at timestamp not null,
	last_time_online timestamp not null
);

create table user_role(
	login varchar(128),
	role_name varchar(64),
	constraint user_role_primary_key primary key(login, role_name),
	constraint login_foreign_key_to_shop_user foreign key(login) references chat_user(email)
);

CREATE TABLE chat
(
	chat_id UUID primary key default gen_random_uuid(),
	last_dispatch_date timestamp
);

CREATE TABLE user_to_chat
(
	chat_id UUID,
	email varchar(100),
	constraint user_chat_primary_key primary key(chat_id, email),
	constraint chat_id_foreign_key_to_chat foreign key(chat_id) references chat(chat_id),
	constraint email_foreign_key_to_chat_user foreign key(email) references chat_user(email)
);

CREATE TABLE chat_message
(
	message_id UUID primary key unique default gen_random_uuid(),
	chat_id UUID not null,
	sender_email varchar(100) not null,
	message_content text not null,
	dispatch_date timestamp not null,
	status varchar(16) not null,
	constraint chat_id_foreign_key_to_chat foreign key(chat_id) references chat(chat_id),
	constraint sender_email_foreign_key_to_chat_user foreign key(sender_email) references chat_user(email)
);