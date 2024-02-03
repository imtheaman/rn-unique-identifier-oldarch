
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNRnUniqueIdentifierSpec.h"

@interface RnUniqueIdentifier : NSObject <NativeRnUniqueIdentifierSpec>
#else
#import <React/RCTBridgeModule.h>

@interface RnUniqueIdentifier : NSObject <RCTBridgeModule>
#endif

@end
