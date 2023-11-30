
# Card manager

Manages users and initializes card creation for users 


## API Reference



#### Creates new customer entity

```http
  GET /customer/create
```

| Request Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required**. Customers name |
| `surname` | `string` | **Required**. Customers surname |
| `oib` | `string` | **Required**. Customers OIB |

#### Find customer by OIB

```http
  GET /customer/find
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `oib` | `string` | **Required**. Customers OIB |

#### Delete customer by OIB

```http
  GET /customer/delete
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `oib` | `string` | **Required**. Customers OIB |

#### Initialize card creation for Customer

```http
  GET /card/initializeCreation/{oib}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `oib` | `string` | **Required**. Customers OIB |
