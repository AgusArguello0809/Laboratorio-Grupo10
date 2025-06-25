// const jsonServer = require('json-server');
// const jwt = require('jsonwebtoken');
// const fs = require('fs');

// const server = jsonServer.create();
// const router = jsonServer.router('./mocks/db.json');
// const middlewares = jsonServer.defaults();

// const JWT_SECRET = 'tu_secreto_super_seguro';

// // Middleware para parsear body
// server.use(jsonServer.bodyParser);
// server.use(middlewares);

// server.post('/auth/login', (req, res) => {
//   const { email, password } = req.body;

//   console.log('Intento de login:', { email });

//   // Leer usuarios desde db.json
//   const dbData = JSON.parse(fs.readFileSync('./mocks/db.json', 'utf8'));
//   const users = dbData.users || [];

//   // Buscar usuario por email o username
//   const user = users.find(u =>
//     (u.email === email || u.username === email) && u.password === password
//   );

//   if (!user) {
//     console.log('Credenciales incorrectas');
//     return res.status(401).json({ message: 'Credenciales incorrectas' });
//   }

//   console.log('Usuario encontrado:', user.username);

//   // Generar token
//   const token = jwt.sign(
//     {
//       id: user.id,
//       name: user.name,
//       lastName: user.lastName,
//       email: user.email,
//       username: user.username
//     },
//     JWT_SECRET,
//     { expiresIn: '24h' }
//   );

//   // Crear objeto de usuario sin contraseña
//   const userWithoutPassword = { ...user };
//   delete userWithoutPassword.password;

//   console.log('Login exitoso, token generado');
//   return res.json({
//     user: userWithoutPassword,
//     token
//   });
// });

// // Middleware para verificar token en rutas protegidas
// server.use((req, res, next) => {
//   const url = req.originalUrl;

//   const isLogin = url.startsWith('/auth/login');
  
//   const isRegister = req.method === 'POST' && (
//     url.startsWith('/users')
//   );
    
//   const isGet = req.method === 'GET';

//   if (isLogin || isRegister || isGet) {
//     return next();
//   }

//   const authHeader = req.headers.authorization;
//   if (!authHeader || !authHeader.startsWith('Bearer ')) {
//     return res.status(401).json({ message: 'No autorizado' });
//   }

//   const token = authHeader.split(' ')[1];

//   try {
//     const decoded = jwt.verify(token, JWT_SECRET);
//     req.user = decoded;
//     next();
//   } catch (err) {
//     return res.status(401).json({ message: 'Token inválido' });
//   }
// });

// // Usar el router de json-server
// server.use(router);

// // Iniciar servidor
// server.listen(3001, () => {
//   console.log('JSON Server con JWT está corriendo en http://localhost:3001');
//   console.log('Puedes acceder a los datos en http://localhost:3001/users');
// });