import { useEffect, useState, useCallback } from 'react';
import { toast } from 'react-toastify';
import { getInventoryProducts } from '../api/hooks/inventory.service';
import { InventoryProduct } from '../types/inventory';
import ProductCard from '../components/ProductCard';
import CreateProductModal from '../components/CreateProductModal';
import { useSSE } from '../api/hooks/useSSE';

const InventoryPage = () => {
  const [products, setProducts] = useState<InventoryProduct[]>([]);
  const [loading, setLoading] = useState(true);
  const [openModal, setOpenModal] = useState(false);

  const loadProducts = async () => {
    try {
      const res = await getInventoryProducts();
      setProducts(res.data.content);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  // 🔥 SSE
  const handleEvent = useCallback((data: any) => {
    toast.info(`🔔 Alguien compró un producto: ${data.productId}`);
    loadProducts();
  }, []);

  useSSE('/api/inventory/notifications/stream', handleEvent);

  if (loading) return <p>Cargando productos...</p>;

  return (
    <>
      <div style={{ position: "fixed", top: "240px" }}>
        <button onClick={() => setOpenModal(true)}>
          ➕ Crear producto
        </button>
      </div>

      <div className="grid">
        {products.map((product) => (
          <ProductCard
            key={product.productId}
            product={product}
            onPurchaseSuccess={loadProducts}
          />
        ))}
      </div>

      {openModal && (
        <CreateProductModal
          onClose={() => setOpenModal(false)}
          onSuccess={loadProducts}
        />
      )}
    </>
  );
};

export default InventoryPage;
