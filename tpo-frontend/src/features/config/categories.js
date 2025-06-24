
export const CATEGORIES = [
  { id: 1, name: "Calzado" },
  { id: 2, name: "Equipamiento" },
  { id: 3, name: "Ropa" },
  { id: 4, name: "Suplementos" },
  { id: 5, name: "Accesorios" }
];
export const getCategoryById = (id) => {
  return CATEGORIES.find(cat => cat.id === id);
};

export const getCategoryByName = (name) => {
  return CATEGORIES.find(cat => cat.name === name);
};

export const getCategoryId = (name) => {
  const category = getCategoryByName(name);
  return category ? category.id : 1; // Default a 1 si no existe
};

export const getCategoryName = (id) => {
  const category = getCategoryById(id);
  return category ? category.name : "Desconocida";
};