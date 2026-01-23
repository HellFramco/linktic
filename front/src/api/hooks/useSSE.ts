import { useEffect } from 'react';

type EventCallback = (data: any) => void;

export const useSSE = (url: string, onMessage: EventCallback) => {
  useEffect(() => {
    const eventSource = new EventSource(url);

    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      onMessage(data);
    };

    eventSource.onerror = () => {
      console.error('SSE connection error');
      eventSource.close();
    };

    return () => eventSource.close();
  }, [url, onMessage]);
};
