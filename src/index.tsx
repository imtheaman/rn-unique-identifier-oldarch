import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'rn-unique-identifier' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const RnUniqueIdentifier = NativeModules.RnUniqueIdentifier
  ? NativeModules.RnUniqueIdentifier
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function getPersistentIdentifier(callback: (uid: string) => void): void {
  RnUniqueIdentifier.getPersistentIdentifier(callback);
}
