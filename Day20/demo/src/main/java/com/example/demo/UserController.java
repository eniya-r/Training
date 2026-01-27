package com.example.demo;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final Map<Long, Map<String, Object>> users = new HashMap<>();

	public UserController() {
		users.put(101L, Map.of("id", 101L, "name", "Alice", "active", true));
		users.put(102L, Map.of("id", 102L, "name", "Bob", "active", false));
		users.put(103L, Map.of("id", 103L, "name", "Charlie", "active", true));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getUserById(@PathVariable("id") Long id) {
		Map<String, Object> user = users.get(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<Map<String, Object>>> listUsers(
			@RequestParam(name = "active", required = false) Boolean active,
			@RequestParam(name = "sort", defaultValue = "id,asc") String sort
	) {
		List<Map<String, Object>> results = new ArrayList<>(users.values());

		if (active != null) {
			results = results.stream()
					.filter(u -> Objects.equals(u.get("active"), active))
					.collect(Collectors.toList());
		}

		String[] parts = sort.split(",");
		String field = parts[0];
		boolean asc = parts.length < 2 || parts[1].equalsIgnoreCase("asc");

		Comparator<Map<String, Object>> cmp = Comparator.comparing(
				m -> String.valueOf(m.get(field))
		);

		if (!asc) {
			cmp = cmp.reversed();
		}

		results.sort(cmp);

		return ResponseEntity.ok(results);
	}

	@GetMapping("/{id}/orders")
	public ResponseEntity<Map<String, Object>> getUserOrders(
			@PathVariable Long id,
			@RequestParam(name = "status", required = false) String status,
			@RequestParam(name = "limit", defaultValue = "10") int limit
	) {
		if (!users.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}

		Map<String, Object> payload = new HashMap<>();
		payload.put("userId", id);
		payload.put("statusFilter", status);
		payload.put("limit", limit);
		payload.put("orders", List.of(
				Map.of("orderId", 5001, "status", "PAID"),
				Map.of("orderId", 5002, "status", "PENDING")
		));

		return ResponseEntity.ok(payload);
	}
}
