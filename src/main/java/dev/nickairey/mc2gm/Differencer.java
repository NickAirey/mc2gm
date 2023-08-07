package dev.nickairey.mc2gm;

import java.util.Collection;

import reactor.core.publisher.Flux;

public class Differencer {

	public static Flux<StringDiff> getDifferences(Collection<String> a, Collection<String> b) {

		return 
			Flux.concat(
				Flux.fromStream(
					b.stream()
					.filter(c -> !a.contains(c))
					.map(StringDiff::bOnly)
				),
				Flux.fromStream(
					a.stream()
					.filter(c -> !b.contains(c))
					.map(StringDiff::aOnly)
				)
			);
	};

	public static enum DiffType {
		AOnly, BOnly, AandB
	}

	public static class StringDiff {
		
		public static StringDiff aOnly(String value) {
			return new StringDiff(value, DiffType.AOnly);
		}
		
		public static StringDiff bOnly(String value) {
			return new StringDiff(value, DiffType.BOnly);
		}
		
		public String value;
		public DiffType diffType;

		private StringDiff(String value, DiffType diffType) {
			this.value = value;
			this.diffType = diffType;
		}

	}
}
