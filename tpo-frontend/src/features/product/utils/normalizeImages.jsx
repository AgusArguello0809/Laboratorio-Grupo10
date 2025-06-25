export function normalizeImages(images) {
    return images.map((img) => {
        if (typeof img === "string") {
            // Imagen persistida (Firebase): se la trata como estática
            return img;
        } else if (img instanceof File) {
            // Imagen subida directamente sin formatear (por si acaso)
            return {
                url: URL.createObjectURL(img),
                file: img
            };
        } else if (img?.file && img?.url) {
            // Imagen nueva ya formateada
            return img;
        } else {
            console.warn("Formato de imagen desconocido:", img);
            return null;
        }
    }).filter(Boolean); // se eliminan nulos
}