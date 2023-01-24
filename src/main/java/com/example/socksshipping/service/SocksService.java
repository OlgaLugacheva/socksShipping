package com.example.socksshipping.service;

import com.example.socksshipping.dto.SocksMapper;
import com.example.socksshipping.exception.EmptyStockException;
import com.example.socksshipping.dto.SockShippingDto;
import com.example.socksshipping.model.Color;
import com.example.socksshipping.model.SocksSize;
import com.example.socksshipping.model.Sock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocksService {

    private static final Map<Sock, Integer> socks = new HashMap<>();
    private final SocksMapper socksMapper;

    public void addSocks(SockShippingDto socksShippingDto) {
        Sock sock = socksMapper.toSocks(socksShippingDto);
        if (socks.containsKey(sock)) {
            socks.put(sock, socks.get(sock) + socksShippingDto.getQuantity());
        } else {
            socks.put(sock, socksShippingDto.getQuantity());
        }
    }

    public void sellSocks(SockShippingDto socksShippingDto) {
        decreaseSockQuantity(socksShippingDto);
    }

    public void removeDefectiveSocks(SockShippingDto socksShippingDto) {
        decreaseSockQuantity(socksShippingDto);
    }

    private void decreaseSockQuantity(SockShippingDto socksShippingDto) {
        Sock sock = socksMapper.toSocks(socksShippingDto);
        int sockQuantity = socks.getOrDefault(sock, 0);
        if (sockQuantity >= socksShippingDto.getQuantity()) {
            socks.put(sock, sockQuantity - socksShippingDto.getQuantity());
        } else {
            throw new EmptyStockException("На складе нет носков.");
        }
    }

    public long getSockQuantity(Color color, SocksSize size, Integer cottonMin, Integer cottonMax) {
        return socks.entrySet().stream()
                .filter(color != null ? s -> color.equals(s.getKey().getColor()) : s -> true)
                .filter(size != null ? s -> size.equals(s.getKey().getSize()) : s -> true)
                .filter(cottonMin != null ? s -> cottonMin <= s.getKey().getCottonContent() : s -> true)
                .filter(cottonMax != null ? s -> cottonMax >= s.getKey().getCottonContent() : s -> true).count();
    }

}
